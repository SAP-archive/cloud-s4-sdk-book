DATA ls_bp      LIKE LINE OF it_bp_root.
DATA ls_message LIKE LINE OF ct_validationmessage.

READ TABLE it_bp_root INTO ls_bp
  WITH KEY
    businesspartner = i_cmd_bp_object_key-businesspartner
    businesspartneruuid = i_cmd_bp_object_key-businesspartneruuid.

" require a search term for all persons (cat=1) 
IF ls_bp-businesspartnercategory EQ 1 AND
  ls_bp-searchterm1 EQ ''.
    ls_message-msgty = 'E'.
    ls_message-msgv1 = |Please maintain a search term|.
    APPEND ls_message TO ct_validationmessage.
ENDIF.