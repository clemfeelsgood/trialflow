class CreateAssessments < ActiveRecord::Migration
  def up
    create_table :assessments do |t|
      t.string :name
      t.string :description
      t.boolean :realized, :default => false
      t.integer :protocol_day
      t.belongs_to :protocole
      t.belongs_to :clinical_study
      t.timestamps
    end
  end

  def down

  end
end
