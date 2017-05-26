package com.danidemi.templategeneratormavenplugin.model;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

public class RowMetaModel implements IRowMetaModel {
    private final long count;
    private final long sourceCount;

    public RowMetaModel(int count, long sourceCount) {
        this.count = count;
        this.sourceCount = sourceCount;
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public long getIndex() {
        return count-1;
    }

    @Override
    public long getSourceIndex() {
        return sourceCount-1;
    }

    @Override
    public long getSourceCount() {
        return sourceCount;
    }
}
