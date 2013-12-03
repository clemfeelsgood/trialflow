class CreateCrfItems < ActiveRecord::Migration
  def up
    create_table :crf_items do |t|
      t.string :name
      t.string :description
      t.string :input_type
      t.string :value
      t.belongs_to :assessment
      t.belongs_to :user
      t.timestamps
    end
  end

  def down
    drop_table :crf_items
  end
end
