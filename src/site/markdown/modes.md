## One Context Per File Extended

It parses one source file to produce one target file.
It uses a context that has all rows in memory.
The meta gives access to multiple info as the total number of rows.
You can access rows as a list.

## One Context Per File

It parses one source file to produce one target file.
It uses a context that gives access to an iterator over rows.

## One Context Per Row

It parses one source file to produce one target file for each row contained in the source file.
Contexts are internally created as an iterator.
You can access the only row as a list.

## One Context Per Tag Extended

It parses one source file to produce one target file for each group of rows contained in the source file identified by a tag.
You can access the rows as a list.
