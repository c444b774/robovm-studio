/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.spellchecker.util;

import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public final class Strings {
  private Strings() {
  }

  public static boolean isCapitalized(String word) {
    if (word.length() == 0) return false;

    boolean lowCase = true;
    for (int i = 1; i < word.length() && lowCase; i++) {
      lowCase = Character.isLowerCase(word.charAt(i));
    }
    return Character.isUpperCase(word.charAt(0)) && lowCase;
  }


  public static boolean isCapitalized(@NotNull String text, @NotNull TextRange range) {
    if (range.getLength() == 0) return false;
    CharacterIterator it = new StringCharacterIterator(text, range.getStartOffset() + 1, range.getEndOffset(), range.getStartOffset() + 1);
    boolean lowCase = true;
    for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
      lowCase = Character.isLowerCase(c);
    }

    return Character.isUpperCase(text.charAt(range.getStartOffset())) && lowCase;
  }

  public static boolean isUpperCased(@NotNull String text, @NotNull TextRange range) {
    if (range.getLength() == 0) return false;
    CharacterIterator it = new StringCharacterIterator(text, range.getStartOffset(), range.getEndOffset(), range.getStartOffset());

    for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
      if (!Character.isUpperCase(c)) {
        return false;
      }
    }

    return true;
  }

  public static boolean isUpperCase(String word) {
    boolean upperCase = true;
    for (int i = 0; i < word.length() && upperCase; i++) {
      upperCase = Character.isUpperCase(word.charAt(i));
    }

    return upperCase;
  }

  public static boolean isMixedCase(String word) {
    if (word.length() < 2) return false;

    String tail = word.substring(1);
    String lowerCase = tail.toLowerCase();
    return !tail.equals(lowerCase) && !isUpperCase(word);
  }

  public static String capitalize(String word) {
    if (word.length() == 0) return word;

    StringBuffer buf = new StringBuffer(word);
    buf.setCharAt(0, Character.toUpperCase(buf.charAt(0)));
    return buf.toString();
  }

  public static void capitalize(List<String> words) {
    for (int i = 0; i < words.size(); i++) {
      words.set(i, capitalize(words.get(i)));
    }
  }

  public static void upperCase(List<String> words) {
    for (int i = 0; i < words.size(); i++) {
      words.set(i, words.get(i).toUpperCase());
    }
  }

 
  private enum WordState {
    NO_WORD, PREV_UC, WORD
  }

  public static void addAll(@NotNull String text, @NotNull TextRange range, @NotNull List<TextRange> result) {
    CharacterIterator it = new StringCharacterIterator(text, range.getStartOffset(), range.getEndOffset(), range.getStartOffset());
    StringBuffer b = new StringBuffer();
    WordState state = WordState.NO_WORD;
    char curPrevUC = '\0';
    int pos = range.getStartOffset();
    for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
      switch (state) {
        case NO_WORD:
          if (!Character.isUpperCase(c)) {
            b.append(c);
            state = WordState.WORD;
          }
          else {
            state = WordState.PREV_UC;
            curPrevUC = c;
          }
          break;
        case PREV_UC:
          if (!Character.isUpperCase(c)) {
            if (b.length() > 0) {
              result.add(new TextRange(pos, pos + b.length()));
              pos += b.length();
            }
            b = new StringBuffer();
            b.append(curPrevUC);
            b.append(c);
            state = WordState.WORD;
          }
          else {
            b.append(curPrevUC);
            state = WordState.PREV_UC;
            curPrevUC = c;
          }
          break;
        case WORD:
          if (Character.isUpperCase(c)) {
            if (b.length() > 0) {
              result.add(new TextRange(pos, pos + b.length()));
              pos += b.length();
            }
            b.setLength(0);
            state = WordState.PREV_UC;
            curPrevUC = c;
          }
          else {
            b.append(c);
          }
          break;
      }
    }
    if (state == WordState.PREV_UC) {
      b.append(curPrevUC);
    }
    result.add(new TextRange(pos, pos + b.length()));
  }


}
