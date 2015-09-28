class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable
  # Destroy the folders that the user owns when the user delete his account
  has_many :folders, :dependent => :destroy
  
  # Destroy the images that the user owns when the user delete his account
  has_many :images, :dependent => :destroy

  has_many :friendships
 
  # Record the friends who accept the request 
  has_many :friends, -> { where "status = 'accepted'"},
  			through: :friendships
  
  # Record the friends who you have sent request to
  has_many :requested_friends, 
  			-> { where "status = 'requested'"}, 
  			:source => :friend, 
  			through: :friendships
  
  # Record the friends who you have received request from
  has_many :pending_friends, 
  			-> { where "status = 'pending'"},
  			:source => :friend, 
  			through: :friendships

  # Return all the users except current user
  def self.all_except(user)
  	where.not(id: user)
	end
end
