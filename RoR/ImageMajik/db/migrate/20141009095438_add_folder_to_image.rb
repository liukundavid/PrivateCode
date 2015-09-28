class AddFolderToImage < ActiveRecord::Migration
  def change
    add_reference :images, :folder, index: true
  end
end
