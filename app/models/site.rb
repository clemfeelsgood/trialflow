class Site < ActiveRecord::Base
  belongs_to :sponsor
  has_many :users
  has_many :patients
end
