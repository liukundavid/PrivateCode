class Folder < ActiveRecord::Base
	has_many :images, :dependent => :destroy
	belongs_to :user

	accepts_nested_attributes_for :user, 
		allow_destroy: true, 
		reject_if: :reject_folder

	# reject folder if user_id is blank
	def reject_folder(attributed)
		attributed['user_id'].blank?
	end
end
