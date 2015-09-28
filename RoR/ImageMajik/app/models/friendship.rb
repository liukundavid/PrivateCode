class Friendship < ActiveRecord::Base
  belongs_to :user
  belongs_to :friend, :class_name => "User", :foreign_key => "friend_id"
  validates_presence_of :user_id, :friend_id

  # Return true if the users are (possibly pending) friends.
  def self.exists?(user, friend)
    not find_by_user_id_and_friend_id(user, friend).nil?
  end

  # Send friend request
  def self.befriend(user, friend)
    unless user == friend or Friendship.exists?(user, friend)
      transaction do
        create(:user => user, :friend => friend, :status => 'requested')
        create(:user => friend, :friend => user, :status => 'pending')
      end
		end
 	end

 	# Accept a friend request.
  def self.accept(user, friend)
    transaction do
      accepted_at = Time.now
      accept_one_side(user, friend, accepted_at)
      accept_one_side(friend, user, accepted_at)
		end 
	end

	# Delete a friendship or cancel a pending request.
  def self.breakup(user, friend)
    transaction do
      # Delete shared albums and images
      shared_image_list = Shareimage.search_by_owner_by_sharedowner(user.id, friend.id)
      shared_folder_list = Sharefolder.search_by_owner_by_sharedowner(user.id, friend.id)
      shared_image_list.each do |shared_image|
        Shareimage.deleteshare(shared_image.image_id, friend.id)
      end
      shared_folder_list.each do |shared_folder|
        Sharefolder.deleteshare(shared_folder.folder_id, friend.id)
      end
      # Delete friendship
      destroy(find_by_user_id_and_friend_id(user, friend))
      destroy(find_by_user_id_and_friend_id(friend, user))
		end 
	end

private
  # Update the db with one side of an accepted friendship request.
  def self.accept_one_side(user, friend, accepted_at)
    request = find_by_user_id_and_friend_id(user, friend)
    request.status = 'accepted'
    request.accepted_at = accepted_at
    request.save!
	end
end