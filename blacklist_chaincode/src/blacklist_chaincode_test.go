package main


import (
	"fmt"
	"reflect"
	"testing"
	"encoding/json"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

func checkInit(t *testing.T, stub *shim.MockStub, args [][]byte) {
	res := stub.MockInit("1", args)
	if res.Status != shim.OK {
		fmt.Println("Init failed", string(res.Message))
		t.FailNow()
	}
}

func checkState(t *testing.T, stub *shim.MockStub, name string, expected []byte) {
	actual := stub.State[name]
	if actual == nil {
		fmt.Println("State", name, "failed to get value")
		t.FailNow()
	}
	if !reflect.DeepEqual(actual, expected) {
		fmt.Println("State value", name, "expected", string(expected), ", but got:", string(actual))
		t.FailNow()
	}
}

func checkStateWithMarshalling(t *testing.T, stub *shim.MockStub, name string, expected interface{}) {
	if expected == nil {
		checkState(t, stub, name, nil)
	} else {
		actual := stub.State[name]
		if actual == nil {
			fmt.Println("State", name, "failed to get value")
			t.FailNow()
		}
		compareWithMarshalling(t, name, expected, actual)
	}
}

func compareWithMarshalling(t *testing.T, name string, expected interface{}, value []byte) {
	expectedType := reflect.TypeOf(expected)

	actual := reflect.New(expectedType)
	err := json.Unmarshal(value, actual.Interface()) // provides interface of *expectedType
	if err != nil {
		fmt.Println("Error in unmarshalling expected", expected, ": ", err)
		t.FailNow()
	}

	// describe(actual.Interface()) // <interface of *expectedType>
	// describe(actual.Elem().Interface()) // <interface of expectedType>
	//describe(actual.Indirect()) // <Value<expectedType>>
	// describe(reflect.ValueOf(expected).Interface()) // <interface of expectedType>
	// describe(expected) // <interface of expected>
	if !reflect.DeepEqual(expected, actual.Elem().Interface()) {
		fmt.Println("Value", name, "expected", expected, ", but got:", actual.Elem().Interface())
		t.FailNow()
	}
}
func describe(v interface{}) {
	fmt.Printf("%v: %T\n", v, v)
}

func makeArgsArrayFromStrings(argsString ...string) [][]byte {
	var argsBytes = make([][]byte, 0)
	for _, argString := range argsString {
		argsBytes = append(argsBytes, []byte(argString))
	}
	return argsBytes
}

func checkInvoke(t *testing.T, stub *shim.MockStub, expected interface{}, args ...string) {
	checkInvokeBytes(t, stub, expected, makeArgsArrayFromStrings(args...))
}
func checkInvokeBytes(t *testing.T, stub *shim.MockStub, expected interface{}, args [][]byte) {
	if len(args) < 1 {
		fmt.Println("Missing args")
		t.FailNow()
	}
	res := stub.MockInvoke("1", args)
	name := string(args[0])
	if res.Status != shim.OK {
		fmt.Println("Invocation", name, "failed", string(res.Message))
		t.FailNow()
	}
	if expected == nil {
		if res.Payload != nil {
			fmt.Println("Invocation", name, "got unexpected non-nil result:", string(res.Payload))
			t.FailNow()
		}
	} else {

		if res.Payload == nil {
			fmt.Println("Invocation", name, "failed to get value, expected", expected)
			t.FailNow()
		}
		switch expected.(type) {
		case []byte:
			if !reflect.DeepEqual(res.Payload, expected) {
				fmt.Println("Invocation value", name, "expected", string(expected.([]byte)),
					", got", string(res.Payload))
				t.FailNow()
			}
		default:
			compareWithMarshalling(t, name, expected, res.Payload)
		}
	}
}

func TestBlacklist_Init(t *testing.T) {
	blacklist := new(BlacklistChaincode)
	stub := shim.NewMockStub("blacklist", blacklist)

	// Init
	checkInit(t, stub, makeArgsArrayFromStrings("init", "Testlist"))

	ts, _ := getTimestampString(stub)
	checkStateWithMarshalling(t, stub, "root", BlacklistRootEntry{Name: "Testlist", Created: ts})
}

func TestBlacklist_Upgrade(t *testing.T) {
	blacklist := new(BlacklistChaincode)
	stub := shim.NewMockStub("blacklist", blacklist)

	// Init
	checkInit(t, stub, makeArgsArrayFromStrings("init", "Testlist"))
	ts, _ := getTimestampString(stub)
	checkStateWithMarshalling(t, stub, "root", BlacklistRootEntry{Name: "Testlist", Created: ts})

	// Upgrade
	checkInit(t, stub, makeArgsArrayFromStrings("init"))
	checkStateWithMarshalling(t, stub, "root", BlacklistRootEntry{Name: "Testlist", Created: ts})
}

func TestBlacklist_AddCount(t *testing.T) {
	blacklist := new(BlacklistChaincode)
	stub := shim.NewMockStub("blacklist", blacklist)

	// Init
	checkInit(t, stub, makeArgsArrayFromStrings("init", "Testlist"))

	ts, _ := getTimestampString(stub)
	checkStateWithMarshalling(t, stub, "root", BlacklistRootEntry{Name: "Testlist", Created: ts})

	checkInvoke(t, stub, nil, "add", "email", "name1@test.test")


	checkInvoke(t, stub, CountEntriesResult{Count:1, HasMore: false}, "count", "email", "name1@test.test")

	checkInvoke(t, stub, nil, "add", "email", "name2@test.test")

	checkInvoke(t, stub, CountEntriesResult{Count:1, HasMore: false}, "count", "email", "name1@test.test")
	checkInvoke(t, stub, CountEntriesResult{Count:1, HasMore: false}, "count", "email", "name2@test.test")

	// add again with same creator - cannot test different creator because stub doesn't allow mocking creator (yet)
	checkInvoke(t, stub, nil, "add", "email", "name1@test.test")

	checkInvoke(t, stub, CountEntriesResult{Count:1, HasMore: false}, "count", "email", "name1@test.test")

}

func TestBlacklist_AddCountRemove(t *testing.T) {
	blacklist := new(BlacklistChaincode)
	stub := shim.NewMockStub("blacklist", blacklist)

	// Init
	checkInit(t, stub, makeArgsArrayFromStrings("init", "Testlist"))

	checkInvoke(t, stub, nil, "add", "email", "name1@test.test")

	checkInvoke(t, stub, CountEntriesResult{Count:1, HasMore: false}, "count", "email", "name1@test.test")

	checkInvoke(t, stub, nil, "remove", "email", "name1@test.test")

	checkInvoke(t, stub, CountEntriesResult{Count:0, HasMore: false}, "count", "email", "name1@test.test")
}
