package com.meignotte.nicolas.vapeur;

/**
 * Created by nicolas on 13/06/17.
 */
public class Review {
    int mark;
    String author;
    String review;
    public Review(){};
    public Review(int mark, String author, String review) {
        this.mark = mark;
        this.author = author;
        this.review = review;
    }

    public int getMark() {

        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
