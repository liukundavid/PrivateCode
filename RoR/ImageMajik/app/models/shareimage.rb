class Shareimage < ActiveRecord::Base
	belongs_to :user
	belongs_to :image

	validates_presence_of :shared_owner, :image_id, :origin_owner

  # Return true if the image is shared to the user
	def self.exists?(image_id, user_id)
    not find_by_image_id_and_shared_owner(image_id, user_id).nil?
  end

  # Return search the record by user_id
  def self.search_by_user(sharedowner)
  	where(shared_owner: sharedowner)
  end

  # Search shared images
  def self.search_by_owner_by_sharedowner(owner, sharedowner)
    where(shared_owner: sharedowner, origin_owner: owner)
  end

  # Add a record
  def self.addtouser(image_id, owner, sharedowner)
    create(:image_id => image_id, :shared_owner => sharedowner, :origin_owner => owner)
  end

  # Delete a record
  def self.deleteshare(image_id, sharedowner)
    destroy(find_by_image_id_and_shared_owner(image_id, sharedowner))
  end

end
