class Sharefolder < ActiveRecord::Base
	belongs_to :user
	belongs_to :folder

	validates_presence_of :shared_owner, :folder_id, :origin_owner

  # Return true if the folder is shared to the user
	def self.exists?(folder_id, user_id)
    not find_by_folder_id_and_shared_owner(folder_id, user_id).nil?
  end

  # Return search the record by user_id
  def self.search_by_user(sharedowner)
  	where(shared_owner: sharedowner)
  end

  # Search shared albums
  def self.search_by_owner_by_sharedowner(owner, sharedowner)
    where(shared_owner: sharedowner, origin_owner: owner)
  end

  # Add a record
  def self.addtouser(folder_id, owner, sharedowner)
  	create(:folder_id => folder_id, :shared_owner => sharedowner, :origin_owner => owner)
  end

  # Delete a record
  def self.deleteshare(folder_id, sharedowner)
  	destroy(find_by_folder_id_and_shared_owner(folder_id, sharedowner))
  end

end
