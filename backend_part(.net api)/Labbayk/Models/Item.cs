using System.ComponentModel.DataAnnotations;

namespace Labbayk.Models
{
    public class Item
    {
        [Key]
        public int Id { get; set; }
        public string Title { get; set; } = string.Empty;
        public Category Category { get; set; }
        public bool BestFood { get; set; }
        public string Description { get; set; } = string.Empty;
        public string ImagePath { get; set; } = string.Empty;
        public double Price { get; set; }
        public Price PriceRange { get; set; }
        public double Star { get; set; }
        public Time TimeRange { get; set; }
        public int TimeValue { get; set; }
        public Restaurant Restaurant { get; set; }
        public int NumberInCart { get; set; }
        public ICollection<OrderItem> OrderItems { get; set; }
    }
}
