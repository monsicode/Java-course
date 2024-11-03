package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SocialFeedPost implements Post {

    private final String id;
    private final UserProfile author;
    private final String content;
    private final LocalDateTime publishedOn;
    private final Map<ReactionType, Set<UserProfile>> reactions;

    public SocialFeedPost(UserProfile author, String content) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.reactions = new HashMap<>();
    }

    @Override
    public String getUniqueId() {
        return id;
    }

    @Override
    public UserProfile getAuthor() {
        return author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null || reactionType == null) {
            throw new IllegalArgumentException("User or reaction has a null value");
        }

        Iterator<Map.Entry<ReactionType, Set<UserProfile>>> iterator = reactions.entrySet().iterator();

        boolean isNewlyAdded = true;

        while (iterator.hasNext()) {
            Map.Entry<ReactionType, Set<UserProfile>> entry = iterator.next();
            //Love ---> user1
            //Love --> -
            //user1 haha
            if (entry.getValue().contains(userProfile) && entry.getKey() != reactionType) {
                entry.getValue().remove(userProfile);

                // not working bc i change the map while iterating

                if (entry.getValue().size() == 0) {
                    iterator.remove();
                }

                isNewlyAdded = false;
                break;

            }
            if (entry.getValue().contains(userProfile) && entry.getKey() == reactionType) {
                return false;
            }

        }

        reactions.putIfAbsent(reactionType, new HashSet<>());
        reactions.get(reactionType).add(userProfile);

        return isNewlyAdded;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User is null");
        }

        Iterator<Map.Entry<ReactionType, Set<UserProfile>>> iterator = reactions.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<ReactionType, Set<UserProfile>> entry = iterator.next();
            if (entry.getValue().contains(userProfile)) {
                entry.getValue().remove(userProfile);

                if (entry.getValue().size() == 0) {
                    iterator.remove();
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        return Collections.unmodifiableMap(reactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type is null");
        }

        if (reactions.containsKey(reactionType)) {
            return reactions.get(reactionType).size();
        } else {
            return 0;
        }
    }

    @Override
    public int totalReactionsCount() {
        int totalReactionCount = 0;

        for (var entry : reactions.entrySet()) {
            totalReactionCount += entry.getValue().size();
        }
        return totalReactionCount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SocialFeedPost that = (SocialFeedPost) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
