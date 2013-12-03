class CreatePatients < ActiveRecord::Migration
  def up
    create_table :patients do |t|
      t.string :uid
      t.belongs_to :protocole
      t.belongs_to :site
      t.timestamps
    end
  end

  def down
    drop_table :patients
  end
end
