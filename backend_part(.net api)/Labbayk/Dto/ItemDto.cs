using Labbayk.Models;

namespace Labbayk.Dto
{
    public class ItemDto
    {
        public int Id { get; set; }
        public string Title { get; set; } = string.Empty;
        public bool BestFood { get; set; }
        public string Description { get; set; } = string.Empty;
        public string ImagePath { get; set; } = string.Empty;
        public double Price { get; set; }
        public double Star { get; set; }
        public int TimeValue { get; set; }
        public int NumberInCart { get; set; }
    }
}
