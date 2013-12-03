class Assessment < ActiveRecord::Base
  belongs_to :protocole
  belongs_to :clinical_study
  has_many :crf_items
end
