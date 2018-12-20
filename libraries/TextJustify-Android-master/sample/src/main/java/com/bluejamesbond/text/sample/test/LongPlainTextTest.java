package com.bluejamesbond.text.sample.test;

/*
 * Copyright 2015 Mathew Kurian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * LongPlainTextTest.java
 * @author Mathew Kurian
 *
 * From TextJustify-Android Library v2.0
 * https://github.com/bluejamesbond/TextJustify-Android
 *
 * Please report any issues
 * https://github.com/bluejamesbond/TextJustify-Android/issues
 *
 * Date: 1/27/15 3:35 AM
 */

import android.os.Bundle;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.sample.R;
import com.bluejamesbond.text.sample.helper.TestActivity;

public class LongPlainTextTest extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addDocumentView("Short sentence.\n\n" +
                getResources().getString(R.string.plain_text), DocumentView.PLAIN_TEXT);
    }
}
