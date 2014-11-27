package com.intellij.openapi.util.diff.contents;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.LineSeparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;

public class DocumentContentWrapper implements DocumentContent {
  @NotNull private final DocumentContent myBase;
  @NotNull private final Document myDocument;

  public DocumentContentWrapper(@NotNull DocumentContent base, @NotNull String text) {
    myBase = base;
    myDocument = EditorFactory.getInstance().createDocument(text);
  }

  @NotNull
  @Override
  public Document getDocument() {
    return myDocument;
  }

  @Nullable
  @Override
  public VirtualFile getHighlightFile() {
    return myBase.getHighlightFile();
  }

  @Nullable
  @Override
  public OpenFileDescriptor getOpenFileDescriptor(int offset) {
    return myBase.getOpenFileDescriptor(offset);
  }

  @Nullable
  @Override
  public Charset getCharset() {
    return null;
  }

  @Nullable
  @Override
  public LineSeparator getLineSeparator() {
    return null;
  }

  @Nullable
  @Override
  public FileType getContentType() {
    return myBase.getContentType();
  }

  @Nullable
  @Override
  public OpenFileDescriptor getOpenFileDescriptor() {
    return getOpenFileDescriptor(0);
  }

  @Override
  public void onAssigned(boolean isAssigned) {
  }
}
