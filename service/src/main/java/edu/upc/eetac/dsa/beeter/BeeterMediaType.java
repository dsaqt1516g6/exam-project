package edu.upc.eetac.dsa.beeter;

/**
 * Created by sergio on 8/09/15.
 */
public interface BeeterMediaType {
    public final static String BEETER_AUTH_TOKEN = "application/vnd.dsa.exams.auth-token+json";
    public final static String BEETER_USER = "application/vnd.dsa.exams.user+json";
    public final static String BEETER_STING = "application/vnd.dsa.exams.sting+json";
    public final static String BEETER_SUBJECT = "application/vnd.dsa.exams.subject+json";
    public final static String BEETER_EXAM = "application/vnd.dsa.exams.exam+json";
    public final static String BEETER_COMMENT = "application/vnd.dsa.exams.comment+json";
    public final static String BEETER_CORRECTION = "application/vnd.dsa.exams.correction+json";
    public final static String BEETER_COMMENT_CORRECTION = "application/vnd.dsa.exams.commentcorrection+json";
    public final static String BEETER_LIKE = "application/vnd.dsa.exams.like+json";
    public final static String BEETER_STING_COLLECTION = "application/vnd.dsa.exams.sting.collection+json";
    public final static String BEETER_EXAM_COLLECTION = "application/vnd.dsa.exams.exam.collection+json";
    public final static String BEETER_SUBJECT_COLLECTION = "application/vnd.dsa.exams.subject.collection+json";
    public final static String BEETER_COMMENT_COLLECTION = "application/vnd.dsa.exams.comment.collection+json";
    public final static String BEETER_CORRECTION_COLLECTION = "application/vnd.dsa.exams.correction.collection+json";
    public final static String BEETER_ROOT = "application/vnd.dsa.exams.root+json";
    public final static String BEETER_ERROR = "application/vnd.dsa.exams.error+json";
}
