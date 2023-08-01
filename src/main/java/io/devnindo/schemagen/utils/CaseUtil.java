/*
 * Copyright 2023 devnindo
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
 */
package io.devnindo.schemagen.utils;

import io.devnindo.datatype.util.Either;

public final class CaseUtil {
    public static final Either<Void, String> ifPrefixedThanSuffix(String name, String... prefixList) {
        Either<Void, String> matchEither = matchPrefix(name, prefixList);
        if (matchEither.isLeft())
            return matchEither;
        else {
            String matchedPrefix = matchEither.right();
            String result = name.substring(matchedPrefix.length());
            // result = camelToSnake(result);
            return Either.right(result);

        }
    }

    public static final Either<Void, String> matchPrefix(String name, String... prefixList) {
        for (String prefix : prefixList) {
            if (name.startsWith(prefix))
                return Either.right(prefix);
        }
        return Either.left(null);
    }

    public static final String pascalToCamel(String str) {
        char firstChar = str.charAt(0);
        firstChar = Character.toLowerCase(firstChar);
        return firstChar + str.substring(1);
    }

    public static final String camelToPascal(String str) {
        char firstChar = str.charAt(0);
        firstChar = Character.toUpperCase(firstChar);
        return firstChar + str.substring(1);
    }

    public static final String toSnake(String str) {
        StringBuilder builder = new StringBuilder();
        int idx = 0;
        for (; idx < str.length(); idx++) {

            Character ch = str.charAt(idx);
            if (Character.isLowerCase(ch))
                break;
            builder.append(Character.toLowerCase(ch));

        }

        for (; idx < str.length(); idx++) {
            char ch = str.charAt(idx);
            if (Character.isUpperCase(ch)) {

                builder.append('_');
                builder.append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /*public static final String camelToSnakeUpper(String str){

        return camelToSnake(str).toUpperCase();
    }*/

    public static final String toSnakeUpper(String str) {

        return toSnake(str).toUpperCase();
    }
}

