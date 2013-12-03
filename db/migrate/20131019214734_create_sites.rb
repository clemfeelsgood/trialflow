class CreateSites < ActiveRecord::Migration
  def up
    create_table :sites do |t|
      t.string :name
      t.string :country
      t.belongs_to :sponsor
      t.timestamps
    end
  end

  def down
    drop_table :sites
  end
end
