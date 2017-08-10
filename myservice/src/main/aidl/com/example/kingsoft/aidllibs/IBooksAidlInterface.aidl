// IBooksAidlInterface.aidl
package com.example.kingsoft.aidllibs;

import com.example.kingsoft.aidllibs.Book;

interface IBooksAidlInterface {
    List<Book> getBooks();
    void addBook(in Book book);
}
