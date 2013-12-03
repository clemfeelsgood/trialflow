class AddDateTime < ActiveRecord::Migration
  def up
    add_column :protocoles, :date, :datetime
    add_column :crf_items, :date, :datetime
  end
  def down
    remove_column :protocoles, :date
    remove_column :crf_items, :date
  end
end
