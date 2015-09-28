class CreateShareimages < ActiveRecord::Migration
  def change
    create_table :shareimages do |t|
      t.integer :image_id
      t.integer :shared_owner

      t.timestamps
    end
  end
end
