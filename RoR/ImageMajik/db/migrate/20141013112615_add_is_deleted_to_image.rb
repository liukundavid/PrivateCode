class AddIsDeletedToImage < ActiveRecord::Migration
  def change
    add_column :images, :is_deleted, :boolean
  end
end
