#Introduction

1. The plug in reads the rows of a specified CSV file.
2. Each row is converted in a map-like structure, called `RowModel` that allows to access the values of all fields.
3. Each row model is evaluated against a row filter that define whether the row should be discarded or not.
4. According to the policy defined in the plug-in, the row models are grouped in different contexts. Possible grouping strategies are:
  * One context per row model.
  * One context with all row models.
  * Assign a set of labels to each row and then group all the rows with the same label.
5. Each context is then evaluated against an optional context filter to decide whether the context should be discarded or not.
6. For each remaining context, a corresponding output file name is generated merging the context with a Velocity template.
7. The context is merged with the template.
8. The resulting file is written to the destination folder. 


```
                           ================
                             CSV source
                           ================
                                |
                                | RowModel
                                |
                                V
                           ================
                             Row Filter     ----> RowModel discarded ----> X
                           ================
                                |
                                | RowModel
                                |
                                V
                           ================
                             Grouping in 
                               Contexts
                           ================
                                |
                                | Context
                                |
                                V
                           ================
                            context filter ----> Context discarded ----> X
                           ================
                                |
                                | Context
                                |
                                V
                           ================
---> Velocity template ---> template merge
                           ================
                                |
                                | file content
                                |
                                V
                           ================
                              filesystem
                           ================             
```