class AddAvatarToImage < ActiveRecord::Migration
  def change
    add_column :images, :avatar, :string
  end
end
