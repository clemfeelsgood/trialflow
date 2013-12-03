class CreateProtocoles < ActiveRecord::Migration
  def up
    create_table :protocoles do |t|
      t.timestamps
      t.belongs_to :clinical_study
    end
  end

  def down
    drop_table :protocoles
  end
end
