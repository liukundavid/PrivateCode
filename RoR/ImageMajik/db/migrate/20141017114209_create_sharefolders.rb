class CreateSharefolders < ActiveRecord::Migration
  def change
    create_table :sharefolders do |t|
      t.integer :folder_id
      t.integer :shared_owner

      t.timestamps
    end
  end
end
