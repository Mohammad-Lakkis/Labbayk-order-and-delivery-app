using System.ComponentModel.DataAnnotations;

namespace Labbayk.Models
{
    public class Restaurant
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public string PhoneNb { get; set; } = string.Empty;
        public string Location { get; set; }

        public ICollection<Item> Items { get; set; }
    }
}
