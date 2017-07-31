package com.campin.Utils;

/**
 * Created by Igor on 7/24/2017.
 */

public class TripComments
{
    private int _commentId;
    private String _userId;
    private String _tripComment;
    private Double _commentScore;

    public TripComments() {
    }

    public TripComments(int _commentId, String _userId, String _tripComment, Double _commentScore) {
        this._commentId = _commentId;
        this._userId = _userId;
        this._tripComment = _tripComment;
        this._commentScore = _commentScore;
    }

    public int get_commentId() {
        return _commentId;
    }

    public void set_commentId(int _commentId) {
        this._commentId = _commentId;
    }

    public String get_tripComment()
    {
        return _tripComment;
    }

    public void set_tripComment(String _tripComment)
    {
        this._tripComment = _tripComment;
    }

    public Double get_commentScore()
    {
        return _commentScore;
    }

    public void set_commentScore(Double _commentScore)
    {
        this._commentScore = _commentScore;
    }

    public String get_userId() {
        return _userId;
    }

    public void set_userId(String _userId) {
        this._userId = _userId;
    }
}
