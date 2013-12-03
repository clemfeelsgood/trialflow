class CreateClinicalStudies < ActiveRecord::Migration
  def up
    create_table :clinical_studies do |t|
      t.string :name
      t.belongs_to :sponsor
      t.timestamps
    end
  end

  def down
    drop_table :clinical_studies
  end
end
