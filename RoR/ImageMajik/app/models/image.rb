class Image < ActiveRecord::Base
	# manage tags
	acts_as_taggable
	ActsAsTaggableOn.remove_unused_tags = true

	# use uploader
	mount_uploader :avatar, AvatarUploader
	belongs_to :folder
	belongs_to :user

	# allow user to use images
	accepts_nested_attributes_for :user, 
		allow_destroy: true, 
		reject_if: :reject_images_without_user
	# allow folder to use images
	accepts_nested_attributes_for :folder, 
		allow_destroy: true, 
		reject_if: :reject_images_without_folder

	# search all versions of a image
    def self.search_image_versions(image_id)
      #ids = [] .each {|image| ids << image.id}
      where(:parent_id => image_id)
      # return ids
    end

    # Search the parent of an image
    def self.search_image_parent(parent_id)
      where(:id => parent_id)
    end


	# user_id should not be blank
	def reject_images_without_user(attributed)
		attributed['user_id'].blank?
	end

	# folder_id should not be blank
	def reject_images_without_folder(attributed)
		attributed['folder_id'].blank?
	end
end
