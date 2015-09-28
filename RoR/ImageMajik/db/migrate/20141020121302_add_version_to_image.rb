class AddVersionToImage < ActiveRecord::Migration
  def change
    add_column :images, :parent_id, :integer
    add_column :images, :version, :integer
  end
end
