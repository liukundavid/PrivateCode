class ShareController < ApplicationController	
	before_action :authenticate_user!
	skip_before_filter :verify_authenticity_token, :only => [:sharefolder, :cancelsharefolder, :shareimage, :cancelshareimage]
	# Goto the share folder page
	def folderpath
		@folders = current_user.folders unless current_user.nil?
		@friends = current_user.friends
		@folder_id = params[:id]
	end

	# Goto the share image page
	def imagepath
		@folders = current_user.folders unless current_user.nil?
		@friends = current_user.friends
		@image_id = params[:id]
	end

	# Get the shared images
	def sharedimages
		@folders = current_user.folders unless current_user.nil?
		@shared_images = []
		Shareimage.where(:shared_owner => current_user.id).each do |sharedimage|
			@shared_images << Image.find(sharedimage.image_id)
		end
	end

	# Get the shared folders
	def sharedfolders
		@folders = current_user.folders unless current_user.nil?
		@shared_folders = []
		Sharefolder.where(:shared_owner => current_user.id).each do |sharedfolder|
			@shared_folders << Folder.find(sharedfolder.folder_id)
		end
	end

	# Share folder
	# Return success json to ajax
	def sharefolder
		#@folder = Folder.find(params[:folder_id])
		#@friend = User.find(params[:friend_id])
		respond_to do |format|
		if Sharefolder.addtouser(params[:folder_id], params[:owner_id], params[:friend_id])
			message = "share succeed"
			format.json{render :json => {:message => message, :status => "200"}}
		else
			message = "share failed"
			format.json{render :json => {:error => message, :status => "400"}}
		end
		end
	end

	# Undo sharing folder action
	# Return success json to ajax
	def cancelsharefolder
		respond_to do |format|
		if Sharefolder.deleteshare(params[:folder_id], params[:friend_id])
			message = "cancel share succeed"
			format.json{render :json => {:message => message, :status => "200"}}
		else
			message = "cancel share failed"
			format.json{render :json => {:error => message, :status => "400"}}
		end
	end
	end

	# Share Image
	# Return success json to ajax
	def shareimage
		#@image = Image.find(params[:image_id])
		#@friend = User.find(params[:friend_id])
		respond_to do |format|
		if Shareimage.addtouser(params[:image_id], params[:owner_id], params[:friend_id])
			message = "share succeed"
			format.json{render :json => {:message => message, :status => "200"}}
		else
			message = "share failed"
			format.json{render :json => {:error_message => message, :status => "400"}}
		end
		end
	end

	# Undo sharing image action
	# Return success json to ajax
	def cancelshareimage
		respond_to do |format|
		if Shareimage.deleteshare(params[:image_id], params[:friend_id])
			message = "cancel share succeed"
			format.json{render :json => {:message => message, :status => "200"}}
		else
			message = "cancel share failed"
			format.json{render :json => {:error => message, :status => "400"}}
		end
		end
	end
end
