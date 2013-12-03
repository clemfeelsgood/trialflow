class AddErrorStateToAssessment < ActiveRecord::Migration
  def change
    add_column :assessments, :error, :boolean, :default => false
  end
end
