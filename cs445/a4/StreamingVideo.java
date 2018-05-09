package cs445.a4;
import cs445.a4.FullCapacityException;


/**
 * This abstract data type represents the backend for a streaming video service.
 * It stores the videos (TV episodes and films), TV series, and users in the
 * system, as well as the like/dislike ratings that users assign to videos.
 */
public interface StreamingVideo {

    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new video to the system. If the system is full, then addVideo throws
     * FullCapacityException without modifying the system. If the video to be added 
     * is null, then addVideo throws NullPointerException without modifying the system.
     * If the system already contains the video that is to be added, addVideo returns
     * false without modifying the system. If the video to be added is not null, and 
     * the system has available capacity and does not contain the video that is to be 
     * added, then addVideo modifies the system so that it now contains this video, 
     * with all other videos in the system remain unmodified. 
     * 
     * @param theVideo video that is to be added to the system
     * @return true if the addition is successful; false if the addition failed
     * @throws NullPointerException if the video is null
     * @throws FullCapacityException if the system has already reached full capacity
     */
    public boolean addVideo(Video theVideo) throws FullCapacityException;

    /**
     * Removes an existing video from the system. If the video to be removed is null,
     * then removeVideo throws IllegalArgumentException without modifying the system.
     * If the system does not contain the video to be removed, then removeVideo will 
     * return false without modifying the system. If the video to be removed is not 
     * null and exists in the system, then removeVideo modifies the system so that the 
     * system does not contain this video any more, with all other videos remain 
     * unmodified.  
     * 
     * @param theVideo video that is to be removed from the system
     * @return true if the removal was successful; false otherwise
     * @throws IllegalArgumentException if the video to be removed does not exist
     * in the system
     * @throws NullPointerException if the video is null
     */
    public boolean removeVideo(Video theVideo);

    /**
     * Adds an existing television episode to an existing television series.
     * If the current capacity of the television series is full, then addToSeries 
     * throws FullCapacityException without modifying the the television series.
     * If the television series is null or the episode is null, then addToSeries
     * returns NullPointerException. If both the television series and the episode
     * are not null, and the television series is not full, then addToSeries
     * modifies the television series so that it contains the episode that is to
     * be added, without modifying other episodes in the television series.
     * 
     * @param theEpisode the episode that is to be added to the the television series
     * @param theSeries the television series to which the episode will be added
     * @return true if the the addition is successful; false otherwise
     * @throws NullPointerException if the episode is null
     * @throws NullPointerException if the television series is null
     * @throws FullCapacityException if the television series is already full
     */
    public boolean addToSeries(Video theEpisode, TvSeries theSeries) throws FullCapacityException;

    /**
     * Removes a television episode from a television series. If the episode to be 
     * removed or the television series is null, then removeFromSeries throws 
     * NullPointerException without any modification to the television series.
     * If the television series does not contain the episode, then removeFromSeries
     * returns false without modifying anything. If the television series contains 
     * the episode to be removed, and both the episode and the television series are 
     * not null, then removeFromSeries modifies the television series so that it no 
     * longer contains this episode, with the other episodes in the television series 
     * remain unmodified. 
     * 
     * @param theEpisode the episode that is to be removed from the television series
     * @param theSeries the television series from which this episode will be removed
     * @return true if the the addition is successful; false if the episode to be
     * to be removed does not exist in the television series.
     * @throws NullPointerException if the episode is null
     * @throws NullPointerException if the television series is null
     */
    public boolean removeFromSeries(Video theEpisode, TvSeries theSeries);

    /**
     * Sets a user's rating for a video, as either "like" or "dislike". 
     * If this video already has this user's rating on it then rateVideo throws 
     * IllegalArgumentException without modifying anything else. If the video to be 
     * rated is null or the user is null, then rateVideo throws NullPointerException 
     * without modifying anything else. If the user has not rated this video, the 
     * video and the user are all not full, and the rating is not null or any invalid 
     * number other than 1 and -1, then rateVideo modifies the the video to 
     * have the user's rating on it. If receive the rating other than 1 or -1, then
     * rateVideo throws IndexOutOfBoundsException.
     * 
     * @param theUser user who will rate for a video
     * @param theVideo video to which the user's rating should be added
     * @param rating the user's rating to the video. Use 1 for like and -1 for unlike
     * @return true if the rate is successfully added; false otherwise
     * @throws IllegalArgumentException if the video already has the user's rating on it
     * @throws NullPointerException if either the user or the video is null
     * @throws IndexOutOfBoundsException if the user's rating is integers other
     * other than 1 or -1
     */
    public boolean rateVideo(User theUser, Video theVideo, int rating);

    /**
     * Clears a user's rating on a video. If this user has rated this video and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this video,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the video
     * @throws NullPointerException if either the user or the video is null
     */
    public void clearRating(User theUser, Video theVideo);
    /**
     * Predicts whether a user will like or dislike a video (that they have not
     * yet rated). If the video or the user is null, then predictRating throws 
     * NullPointerException, with anything else remain unchanged. If this video has 
     * already been rated by this user, predictRating throws IllegalArgumentException 
     * without modifying anything else. If the video has not been rated by the user, and 
     * the user and the video are not null, then predictRating modifies the user so that
     * there will be a rating of this video on this user's record. The specific rating 
     * process will be as: if there are any other users have ever rated this video, compare
     * the frequency of "lile" and "dislike," this particular user will be predicted to have the 
     * same rating as the higher frequency; if no, ramdonly assign the user with "like" 
     * or "dislike."
     * 
     * @param theUser user whose rating on the video will be predicted
     * @param theVideo video that will be rated via prediction of the the user's 
     * preference
     * @return true if the rating is successfully predicted and added to user's record;
     * false otherwise
     * @throws IllegalArgumentException if the video has already been rated by the user
     * @throws NullPointerException if either the user or the video is null
     */
    public boolean predictRating(User theUser, Video theVideo);

    /**
     * Suggests a video for a user that they are predicted to like. If the user
     * is null, suggestVideo throws NullPointerException without modifying anything else. 
     * If the user is not null but does not currently have a rating of "like" on record for 
     * any video, then continuously predict the user's ratings of videos from the system. 
     * Once there is a video that is predicted as "like" by the user, stop the predicting 
     * process and return this video; if there is still not any "like" ratings predicted for
     * the user, then return null. If the user is not null and currently has one or more 
     * than one "like" ratings on record of differnt videos, then randomly choose one video 
     * from those to return as a suggested video for the user.
     * 
     * @param theUser user who will be suggested a video
     * @return the video that will be suggested or null
     * @throws NullPointerException if either the user is null
     */
    public Video suggestVideo(User theUser);


}

