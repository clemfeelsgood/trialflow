class AddStateToProtocole < ActiveRecord::Migration
  def change
    add_column :protocoles, :state, :string, :default => "pending"
  end
end
