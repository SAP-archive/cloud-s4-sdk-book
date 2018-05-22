IF socialmediaaccount-service NE 'TWITTER'.
    message = VALUE #(
        severity = co_severity-warning
        text     = 'Only Twitter accounts can be validated.'
    ).
    RETURN.
ENDIF.

DATA(lo_client) = cl_ble_http_client=>create(
    communication_scenario = 'YY1_TWITTER'
    outbound_service       = 'YY1_CHECKACCOUNT_REST'
).

DATA(request) = cl_ble_http_request=>create(
)->set_method( 'HEAD' 
)->set_resource_extension( socialmediaaccount-account ).

TRY.
    DATA(response) = lo_client->send( request ).
    message = VALUE #(
      severity = co_severity-success
      text     = 'Account @' && socialmediaaccount-account 
                 && ' is valid.'
    ).
    CATCH cx_ble_http_exception INTO DATA(lx).
        message = VALUE #(
            severity = co_severity-error
            text     = 'Error account not found! Status code: '
                       && lx->status_code
        ).
ENDTRY.