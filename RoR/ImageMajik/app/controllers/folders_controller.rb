class FoldersController < ApplicationController
  before_action :set_folder, only: [:show, :edit, :update, :destroy]
  before_action :authenticate_user!, except: [:welcome]
  
  # Get welcome page if no user login
  def welcome
    # Redirect to index page when user login
    if user_signed_in?
      @folders = current_user.folders unless current_user.nil?
      render :index
    else
      render :welcome
    end
    
  end

  # Get Trash Bin page
  # Return the images that are deleted
  def trash
    @trash_images = current_user.images.where(is_deleted: 't')
    @folders = current_user.folders unless current_user.nil?
  end
  # GET /folders
  # GET /folders.json
  # Return the user's folders if current_user exists
  def index
    # @folders = Folder.all
    @folders = current_user.folders unless current_user.nil?
  end

  # GET /foldefolders_pathrs/1
  # GET /folders/1.json
  def show
    #@images = @folder.images.find_by([is_deleted: 'f'])
    @folders = current_user.folders unless current_user.nil?

    if params[:tag]
      # return the images with specific tags if the page pass the tag param
      @images = @folder.images.tagged_with(params[:tag]).where(is_deleted: 'f')
    elsif params[:image_ids]
      # get images according to image id
      image_ids = params[:image_ids]
      image_ids.split(",").each {|image_id| @images = Image.where(:id => image_id, :is_deleted => 'f')}
      # @images = params[:images]
    else
      # only return the images that are not deleted
      @images = @folder.images.where(is_deleted: 'f')
    end

  end

  # GET /folders/new
  def new
    @folder = Folder.new
    @folders = current_user.folders unless current_user.nil?
    # Add the ownership of folder
    @folder.user_id = current_user.id
    @image = @folder.images.build
  end

  # GET /folders/1/edit
  def edit
    @folders = current_user.folders unless current_user.nil?
  end

  # POST /folders
  # POST /folders.json
  def create
    @folder = Folder.new(folder_params)
    # Add the ownership of folder
    @folder.user_id = current_user.id
    respond_to do |format|
      if @folder.save

        format.html { redirect_to @folder, notice: 'Folder was successfully created.' }
        format.json { render :show, status: :created, location: @folder }
      else
        format.html { render :new }
        format.json { render json: @folder.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /folders/1
  # PATCH/PUT /folders/1.json
  def update
    respond_to do |format|
      if @folder.update(folder_params)
        format.html { redirect_to @folder, notice: 'Folder was successfully updated.' }
        format.json { render :show, status: :ok, location: @folder }
      else
        format.html { render :edit }
        format.json { render json: @folder.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /folders/1
  # DELETE /folders/1.json
  def destroy
    @folder.destroy
    respond_to do |format|
      format.html { redirect_to folders_url, notice: 'Folder was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_folder
      @folder = Folder.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    # Destroy the images when the folder is destroyed
    def folder_params
      params.require(:folder).permit(:name, :user_id, images_attributes: [:name, :folder_id, :avatar, :id, :tag_list, :_destroy])
    end
end
