class Protocole < ActiveRecord::Base
  belongs_to :clinical_study
  has_one :patient
  has_many :assessments
end
