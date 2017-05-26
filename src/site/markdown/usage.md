## Model

This is a brief description of the model.

    {
        "rows": [
            {
                "data": {
                    "field-1": "value-of-header-1-in-row-1",
                    "field-2": "value-of-header-2-in-row-1",
                    "field-n": "value-of-header-n-in-row-1"
                },
                "meta": {
                    "index": "0",
                    "rowCount": "1",
                    "sourceIndex": "0-based-index-of-row-1-in-the-source-file",
                    "sourceCount": "1-based-index-of-row-1-in-the-source-file"
                }
            },
            {
                "data": {
                    "field-1": "value-of-header-1-in-row-2",
                    "field-2": "value-of-header-2-in-row-2",
                    "field-n": "value-of-header-n-in-row-2"
                },
                "meta": {
                    "index": "1",
                    "rowCount": "2",
                    "sourceIndex": "0-based-index-of-row-2-in-the-source-file",
                    "sourceCount": "1-based-index-of-row-2-in-the-source-file"
                }
            },
            {
                "data": {
                    "field-1": "value-of-header-1-in-row-m",
                    "field-2": "value-of-header-2-in-row-m",
                    "field-n": "value-of-header-n-in-row-m"
                },
                "meta": {
                    "index": "m-1",
                    "rowCount": "m",
                    "sourceIndex": "0-based-index-of-row-m-in-the-source-file",
                    "sourceCount": "1-based-index-of-row-m-in-the-source-file"
                }
            }  
        ],
        "meta": {
            "template" : {
                "path" : "<path-to-the-template-file>",
                "name" : "<name-of-template-file>"
            },
            "source" : {
                "path": "<path-to-the-csv-file>",
                "name": "<name-of-csv-file>"
            },
            "target" : {
                "path": "<path-to-the-generated-file>",
                "name": "<name-of-generated-file>"            
            },
            "count" : {
                "rows": "<total-number-of-rows>",
                "lastIndex": "<0-based-index-of-last-row>"
            },
            "tags": [ "<tag-1>", "<tag-2>", "<tag-n>"]
        }
    }