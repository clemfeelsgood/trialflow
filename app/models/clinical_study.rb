class ClinicalStudy < ActiveRecord::Base
  has_many :protocoles
  has_many :assessments
  belongs_to :sponsor
end
