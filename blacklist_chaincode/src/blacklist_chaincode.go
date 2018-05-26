package main

import (
	"fmt"
	"time"
	"errors"
	"encoding/json"
	"encoding/base64"


	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

var logger = shim.NewLogger("blacklistChaincode")

// chaincode entry for the blacklist itself:
type BlacklistRootEntry struct {
	Name    string `json:"name"`
	Created string `json:"created"`
}

// chaincode entry for a single blacklist listing, this could hold additional information with regards to the entry
type BlacklistEntry struct {
	// here entry-specific information can be added. For example you might implement an additional level
	// of security and add the signature in here. This might be required if not every user will be a hyperledger user
}

type BlacklistUpdateEvent struct {
	ObjectType string `json:"objectType"`
	Value      string `json:"value"`
}

type BlacklistChaincode struct {
}

func main() {
	err := shim.Start(new(BlacklistChaincode))
	if err != nil {
		fmt.Printf("Error starting Blacklist chaincode: %s", err)
	}
}

// Init is being executed during initial setup and also for an upgrade transaction
func (t *BlacklistChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	_, args := stub.GetFunctionAndParameters()
	logger.Debugf("Blacklist Init(%s) called", args)

	blacklistContent, err := stub.GetState("root");
	if err != nil {
		logger.Errorf("GetState error in Init: %s", err)
		return shim.Error("Failed to get state, check log")
	}

	if blacklistContent != nil {
		// Upgrade!
		oldRoot := BlacklistRootEntry{};
		err := json.Unmarshal(blacklistContent, &oldRoot)
		if err != nil {
			logger.Errorf("Unmarshalling error in Init during upgrade: %s", err)
			return shim.Error("Failed to get upgrade, check log")
		}
		logger.Infof("Upgrade of chaincode for blacklist named '%s' finished", oldRoot.Name)

	} else {
		// Init!
		if len(args) != 1 {
			return shim.Error(
				fmt.Sprintf("Incorrect number of arguments. " +
				"Expecting 1 name for the blacklist to create. Ignored on upgrade. Got: %s", args))
		}

		blacklistName := args[0]

		timestamp, err := getTimestampString(stub)
		if err != nil {
			logger.Errorf("Timestamp error: %s", err)
			return shim.Error("Failed to get timestamp")
		}

		newEntry := &BlacklistRootEntry{
			Created: timestamp,
		}
		newEntry.Name = blacklistName
		newEntryBytes, err := json.Marshal(newEntry)
		if err != nil {
			logger.Errorf("Failed to encode new entry: %s", err)
			return shim.Error("Failed to encode new entry")
		}

		stub.PutState("root", newEntryBytes)
		logger.Infof("Instantiation of chaincode for blacklist named '%s' finished", blacklistName)
	}

	return shim.Success(nil)
}

func (t *BlacklistChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	method, args := stub.GetFunctionAndParameters()
	logger.Debug("Invoke execution for method %s", method)

	switch method {
	case "add":
		return t.add(stub, args)
	case "count":
		return t.countEntries(stub, args)
	case "remove":
		return t.remove(stub, args)
	default:
		return shim.Error("Unknown method")
	}
}

func (t *BlacklistChaincode) add(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	if len(args) != 2 {
		return shim.Error("Needs 2 args: type, value")
	}
	objectType := args[0]
	blacklistValue := args[1]

	entryKey, err := createCompositeEntryKey(stub, objectType, blacklistValue)
	if err != nil {
		logger.Error("Cannot add entry:", err)
		return shim.Error("Cannot add entry, composite key creation failed")
	}

	entryValue := BlacklistEntry{}
	entryValueBytes, err := json.Marshal(entryValue)
	if err != nil {
		logger.Errorf("Marshalling of entry failed: %s", entryValue)
		return shim.Error("Marshalling of entry failed")
	}

	putErr := stub.PutState(entryKey, entryValueBytes)
	if putErr != nil {
		logger.Errorf("PutState failed in add: %s", putErr)
		return shim.Error("Could not add entry: Failure to write to chain")
	}

	t.sendUpdateEvent(stub, objectType, blacklistValue)

	return shim.Success(nil)
}

func (t *BlacklistChaincode) sendUpdateEvent(stub shim.ChaincodeStubInterface, objectType string, blacklistValue string) error {
	eventContent := BlacklistUpdateEvent{ObjectType: objectType, Value: blacklistValue}

	eventBytes, err := json.Marshal(eventContent)
	if err != nil {
		logger.Errorf("Could not send update event for %s:%s reason: Json marshalling failed: %s",
			objectType, blacklistValue, err)
		return err
	}

	if err := stub.SetEvent("update", eventBytes); err != nil {
		logger.Errorf("Could not send update event for %s:%s reason: %s", objectType, blacklistValue, err)
		return err
	}

	return nil
}

func (t *BlacklistChaincode) remove(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 2 {
		return shim.Error("Needs 2 args: type, value")
	}
	objectType := args[0]
	blacklistValue := args[1]

	entryKey, err := createCompositeEntryKey(stub, objectType, blacklistValue)
	if err != nil {
		logger.Error("Cannot add entry:", err)
		return shim.Error("Cannot add entry, composite key creation failed")
	}

	delErr := stub.DelState(entryKey)
	if delErr != nil {
		logger.Errorf("DelState failed in remove: %s", delErr)
		return shim.Error("Could not remove entry: Failure to write to chain")
	}

	t.sendUpdateEvent(stub, objectType, blacklistValue)

	return shim.Success(nil)
}

func createCompositeEntryKey(stub shim.ChaincodeStubInterface, objectType string, blacklistValue string) (string, error) {
	creatorBytes, err := stub.GetCreator()
	if err != nil {
		logger.Errorf("Creator unavailable: %s", err)
		return "", errors.New("Creator unavailable")
	}
	creatorB64 := base64.StdEncoding.EncodeToString(creatorBytes)
	if err != nil {
		logger.Errorf("Encoding of creator %x failed, %s", creatorBytes, err)
		return "", errors.New("Encoding creator failed")
	}
	entryKey, err := stub.CreateCompositeKey(objectType, []string{blacklistValue, creatorB64})
	if err != nil {
		logger.Errorf("Creation of composite key of '%s','%s','%s' failed: %s", objectType, blacklistValue, creatorBytes, err)
		return "", errors.New("Creating composite key failed")
	}
	return entryKey, nil
}

type CountEntriesResult struct {
	Count float64 `json:"count"`
	HasMore bool `json:"hasMore"`
}

func (t *BlacklistChaincode) countEntries(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 2 {
		return shim.Error("Needs 2 args: type, value")
	}
	objectType := args[0]
	blacklistValue := args[1]

	stateIter, err := stub.GetStateByPartialCompositeKey(objectType, []string{blacklistValue})
	if err != nil {
		logger.Errorf("Querying with '%s','%s' failed: %s", objectType, blacklistValue, err)
		return shim.Error("Querying failed")
	}
	var count int = 0
	var iterationFailed = false
	var hasMore = false
	for stateIter.HasNext() {
		if count > 99 {
			hasMore = true
			break // for performance reasons
		}

		count++
		state, err := stateIter.Next()
		if err != nil {
			logger.Errorf("Iterating results failed: %s", err)
			iterationFailed = true
			break
		}

		logger.Debugf("Counting for query '%s','%s': '%s', count: %d'", objectType, blacklistValue,
			state, count)

	}
	stateIter.Close()
	if iterationFailed {
		return shim.Error("Iterating results failed")
	}

	resultValue := CountEntriesResult{
		Count: float64(count),
		HasMore: hasMore,
	}
	resultValueBytes, err := json.Marshal(resultValue)
	if err != nil {
		logger.Errorf("Marshalling results failed: %s", err)
		return shim.Error("Marshalling results failed")
	}

	return shim.Success(resultValueBytes)
}

func getTimestampString(stub shim.ChaincodeStubInterface) (string, error) {
	timestamp, err := stub.GetTxTimestamp()
	if err != nil {
		return "", err
	}
	date := time.Unix(timestamp.Seconds, int64(timestamp.Nanos)).UTC()
	dateString := date.Format("2006-01-02T00:00:00")
	return dateString, nil
}
