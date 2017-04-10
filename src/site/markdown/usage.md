## Model

This is a brief description of the model.

    {
        rows: [
            {
                field-1: value-1,
                field-2: value-2,
                ...,
                field-n: value-n,
                meta: {
                    rowIndex: 0-based-index-of-this-row,
                    rowCount: 1-based-index-of-this-row,
                    sourceIndex: 0-based-index-of-row-in-the-source-file,
                    sourceCount: 1-based-index-of-row-in-the-source-file,
                }
            },
        ],
        meta: {
            rowCount: <total-number-of-rows>,
            lastIndex: <max-rowIndex>
            sourceFilePath: <path-to-the-csv-file>,
            sourceFileName: <source-file-name>,
            tags: [ <tag-1>, <tag-2>, ..., <tag-n>]
        }
    }