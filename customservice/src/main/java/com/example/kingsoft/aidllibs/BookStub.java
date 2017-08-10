package com.example.kingsoft.aidllibs;

import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class BookStub extends IBooksAidlInterface.Stub {
    private List<Book> mBooks = null;

    public BookStub(){
        if (null == mBooks)
            mBooks = new ArrayList<>();
        mBooks.add(new Book("雷军自传", "雷军", "名人传", 20.6f));
        mBooks.add(new Book("马云自传", "马云", "名人传", 30.6f));
        mBooks.add(new Book("乔布斯自传", "乔布斯", "名人传", 40.6f));
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        return mBooks;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        mBooks.add(book);
    }
}
