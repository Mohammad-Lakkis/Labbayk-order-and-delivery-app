using System.ComponentModel.DataAnnotations;

namespace Labbayk.Models
{
    public class Category
    {
        [Key]
        public int Id { get; set; }
        public string ImagePath { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
        public ICollection<Item> Items { get; set; }
    }
}
