class ImagesController < ApplicationController
  before_action :set_image, only: [:show, :edit, :update, :destroy]
  before_action :authenticate_user!
  skip_before_filter :verify_authenticity_token, :only => [:create_version]
  # GET /images
  # GET /images.json
  # Return the images that are not deleted
  def index
    @images = Image.where(is_deleted: false)
    #@folder_id = current_folder.id
  end

  # GET /images/1
  # GET /images/1.json
  def show
  end

  # Create a new version when the user want to save
  # Return a json to ajax
  def create_version
      #data = params[:data_uri]
      @image = Image.new
      # fetch the raw image by id
      @raw_image = Image.find(params[:id])
      # assign the attributes of raw image to new image
      @image.folder_id = @raw_image.folder_id
      @image.user_id = @raw_image.user_id
      @image.parent_id = @raw_image.id
      @image.version = calculate_version(@image.parent_id)
      @image.is_deleted = false
      @image.name = @raw_image.name + "_" + @image.version.to_s
      image_url = "#{Rails.root}/public/uploads/tmp/#{@image.name}.jpeg"
      
      # Generate the image in server
      File.open(image_url, 'wb') do |f|
        f.write(params[:image].read)
      end

      # Assign the image to uploader
      @image.avatar = File.open(image_url)

      # Return success json
      respond_to do |format|
        if @image.save!
          message = "new version is saved"
          format.json{render :json => {:image_url => @image.avatar_url(),:message => message, :status => "200"}}
        else
          message = "new version save failed"
          format.json{render :json => {:error => message, :status => "400"}}
        end
      end
  end




  # GET /images/new
  def new
    @folders = current_user.folders unless current_user.nil?
    @image = Image.new
    # assign the default values
    @image.folder_id = params[:folder_id]
    @image.user_id = current_user.id
    @image.parent_id = 0
    @image.is_deleted = 0
    @image.version = 0
    @folder = Folder.find(@image.folder_id)
  end

  # GET /images/1/edit
  # Return the folders of current user if the user exists
  def edit
    @folders = current_user.folders unless current_user.nil?
  end

  # Download image
  def download
    @image = Image.find(params[:id])
        send_file(@image.avatar.path,
              :disposition => 'attachment',
              :url_based_filename => false)
  end

  # Generate the data in the edit_filter page
  def edit_filter
    @folders = current_user.folders unless current_user.nil?
    @image = Image.find(params[:id])
    render :file => 'app/views/filter/edit_filter.html.erb'
  end

  # POST /images
  # POST /images.json
  def create
    @image = Image.new(image_params)
    # Assign the default values
    @image.user_id = current_user.id
    @image.is_deleted = 0
    @image.parent_id = 0
    @image.version = 0
    respond_to do |format|
      if @image.save
        format.html { redirect_to "/folders/#{@image.folder_id}", notice: 'Image was successfully created.' }
        format.json { render :show, status: :created, location: @image }
      else
        format.html { render :new }
        format.json { render json: @image.errors, status: :unprocessable_entity }
      end
    end
  end


  # PATCH/PUT /images/1
  # PATCH/PUT /images/1.json
  def update
    @folders = current_user.folders unless current_user.nil?
    respond_to do |format|
      if @image.update(image_params)
        format.html { redirect_to "/folders/#{@image.folder_id}", notice: 'Image was successfully updated.' }
        format.json { render :show, status: :ok, location: @image }
      else
        format.html { render :edit }
        format.json { render json: @image.errors, status: :unprocessable_entity }
      end
    end
  end

  # POST
  # remove the image to trash bin
  def delete
    @image = Image.find(params[:id])
    # @image.is_deleted = true
    if @image.update(:is_deleted => 1)
      redirect_to :back
    end

  end

  # POST
  # undo the delete action
  def undo_delete
    @image = Image.find(params[:id])
    # @image.is_deleted = false
    if @image.update(:is_deleted => 0)
      redirect_to :back
    end
  end

  # DELETE /images/1
  # DELETE /images/1.json
  def destroy
    @image.destroy
    respond_to do |format|
      format.html { redirect_to :back, notice: 'Image was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_image
      @image = Image.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def image_params
      params.require(:image).permit(:name, :folder_id, :user_id, :is_deleted, :tag_list, :avatar)
    end

    # calculate the version of the new image based on the previous versions
    def calculate_version(parent_id)
      return (Image.where(:parent_id => parent_id).nil?) ? 1 : (Image.where(:parent_id => parent_id).length + 1)
    end

    # generate the version string of the new image
    def calculate_full_version(version, parent_id)
      full_version = version.to_s
      while parent_id != 0 do
        full_version = parent_id.to_s + "_" + full_version 
        parent_id = Image.find(parent_id).parent_id
      end
      return full_version
    end
end
