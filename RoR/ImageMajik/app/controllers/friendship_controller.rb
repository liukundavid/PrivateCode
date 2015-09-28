class FriendshipController < ApplicationController
	before_action :set_friends, except: [:index]
	before_action :authenticate_user!
  skip_before_filter :verify_authenticity_token, except: [:index]
 #  Get all users except current user
	def index
		@users = User.all_except(current_user)
    @folders = current_user.folders unless current_user.nil?
  end

  # POST a request to a user
  # Return success json data to ajax
  def befriend
  	#friend = User.find(params[:friend_id])
    respond_to do |format|
  		if Friendship.befriend(@user, @friend)
  			message = "Friend request succeed"
        format.json{render :json => {:message => message, :status => "200"}}
  		else
  			error = "No friendship request from #{@friend.nick_name}."
        format.json{render :json => {:error => error, :status => "400"}}
  		end
    end
  end

  # POST an accept request
  # Return success json data to ajax
  def accept_friend
  	if @user.pending_friends.include?(@friend)
      Friendship.accept(@user, @friend)
      message = "Friendship with #{@friend.nick_name} accepted!"
      format.json{render :json => {:message => message, :status => "200"}}
    else
      error = "No friendship request from #{@friend.nick_name}."
      format.json{render :json => {:error => error, :status => "400"}}
		end
    #redirect_to :back
  end

  # POST a decline request
  # Return success json data to ajax
  def decline_friend
  	if @user.pending_friends.include?(@friend)
      Friendship.breakup(@user, @friend)
      message = "Friendship with #{@friend.nick_name} declined"
      format.json{render :json => {:message => message, :status => "200"}}
    else
      error = "No friendship request from #{@friend.nick_name}."
      format.json{render :json => {:error => error, :status => "400"}}
    end
    #redirect_to :back
  end

  # POST a cancel request
  # Return success json data to ajax
  def cancel_friend
    respond_to do |format|
    if @user.requested_friends.include?(@friend)
      Friendship.breakup(@user, @friend)
      message = "Friendship request canceled."
      format.json{render :json => {:message => message, :status => "200"}}
    else
      error = "No request for friendship with #{@friend.nick_name}"
      format.json {render :json => {:error => error, :status => "400"}}
    end
  end
    #redirect_to :back
  end

  # POST a delete friend request
  # Return success json data to ajax
  def delete_friend
  	#friend = User.find(params[:friend_id])
    respond_to do |format|
  	if @user.friends.include?(@friend)
      Friendship.breakup(@user, @friend)
      message = "Friendship with #{@friend.nick_name} deleted!"
      format.json{render :json => {:message => message, :status => "200"}}
    else
      error = "You aren't friends with #{@friend.nick_name}"
      format.json { render :json => {:error => error, :status => "400"} }
    end
  end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_friends
      @user = current_user
      @friend = User.find(params[:friend_id].to_i)
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    #def friendship_params
    #  params.require(:friendship).permit(:user_id, :friend_id)
    #end
end
