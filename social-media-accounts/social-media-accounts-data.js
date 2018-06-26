/*
 * This module hosts the initial data of the mock server.
 * Replace the content of the array "data" with the intended demo data.
 * Replace the value of deferred (non-expanded) navigation properties with the appropriate empty value, also recursively:
 * - for multi-valued propeties: { "results": [] }
 * - for single-valued properties: null
 */
module.exports = {
    data: [
        {
            "__metadata": {
                "id": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_BPSOCIALMEDIA(guid'00163e30-2f0c-1ee8-a5af-cf6c35b4a827')",
                "uri": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_BPSOCIALMEDIA(guid'00163e30-2f0c-1ee8-a5af-cf6c35b4a827')",
                "type": "YY1_BPSOCIALMEDIA_CDS.YY1_BPSOCIALMEDIAType"
            },
            "SAP_UUID": "00163e30-2f0c-1ee8-a5af-cf6c35b4a827",
            "BusinessPartner": "1003764",
            "to_SocialMediaAccount": {
                "results": [
                    {
                        "__metadata": {
                            "id": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_SOCIALMEDIAACCOUNT_BPSO000(guid'00163e30-2f0c-1ee8-a5af-cf6c35b4e827')",
                            "uri": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_SOCIALMEDIAACCOUNT_BPSO000(guid'00163e30-2f0c-1ee8-a5af-cf6c35b4e827')",
                            "type": "YY1_BPSOCIALMEDIA_CDS.YY1_SOCIALMEDIAACCOUNT_BPSO000Type"
                        },
                        "SAP_UUID": "00163e30-2f0c-1ee8-a5af-cf6c35b4e827",
                        "SAP_PARENT_UUID": "00163e30-2f0c-1ee8-a5af-cf6c35b4a827",
                        "Service": "Twitter",
                        "Account": "SAP",
                        "to_BPSOCIALMEDIA": null
                    },
                    {
                        "__metadata": {
                            "id": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_SOCIALMEDIAACCOUNT_BPSO000(guid'00163e30-2f0c-1ee8-a5af-cf6c35b4c827')",
                            "uri": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_SOCIALMEDIAACCOUNT_BPSO000(guid'00163e30-2f0c-1ee8-a5af-cf6c35b4c827')",
                            "type": "YY1_BPSOCIALMEDIA_CDS.YY1_SOCIALMEDIAACCOUNT_BPSO000Type"
                        },
                        "SAP_UUID": "00163e30-2f0c-1ee8-a5af-cf6c35b4c827",
                        "SAP_PARENT_UUID": "00163e30-2f0c-1ee8-a5af-cf6c35b4a827",
                        "Service": "Facebook",
                        "Account": "SAP",
                        "to_BPSOCIALMEDIA": null
                    }
                ]
            }
        },
        {
            "__metadata": {
                "id": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_BPSOCIALMEDIA(guid'00163e30-2f0c-1ee8-a5af-d29e5350283a')",
                "uri": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_BPSOCIALMEDIA(guid'00163e30-2f0c-1ee8-a5af-d29e5350283a')",
                "type": "YY1_BPSOCIALMEDIA_CDS.YY1_BPSOCIALMEDIAType"
            },
            "SAP_UUID": "00163e30-2f0c-1ee8-a5af-d29e5350283a",
            "BusinessPartner": "1003767",
            "to_SocialMediaAccount": {
                "results": [
                    {
                        "__metadata": {
                            "id": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_SOCIALMEDIAACCOUNT_BPSO000(guid'00163e30-2f0c-1ee8-a5af-d29e5350483a')",
                            "uri": "https://{host}:{port}/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS/YY1_SOCIALMEDIAACCOUNT_BPSO000(guid'00163e30-2f0c-1ee8-a5af-d29e5350483a')",
                            "type": "YY1_BPSOCIALMEDIA_CDS.YY1_SOCIALMEDIAACCOUNT_BPSO000Type"
                        },
                        "SAP_UUID": "00163e30-2f0c-1ee8-a5af-d29e5350483a",
                        "SAP_PARENT_UUID": "00163e30-2f0c-1ee8-a5af-d29e5350283a",
                        "Service": "Twitter",
                        "Account": "sapcp",
                        "to_BPSOCIALMEDIA": null
                    }
                ]
            }
        }
    ]
};