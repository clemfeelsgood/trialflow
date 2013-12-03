class Patient < ActiveRecord::Base
  belongs_to :protocole
  belongs_to :site
end
