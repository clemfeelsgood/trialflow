class AddDescriptionClinicalStudy < ActiveRecord::Migration
  def change
    add_column :clinical_studies, :description, :string
  end
end
