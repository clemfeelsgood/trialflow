class CrfItem < ActiveRecord::Base
  belongs_to :assessment
  belongs_to :user
end
